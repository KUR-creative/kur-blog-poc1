(ns kur.blog.monitor
  "Monitor directory and call update-fn by sending message to channel"
  (:require [babashka.fs :as fs]
            [clojure.core.async :as async]
            [hawk.core :as hawk]
            [kur.blog.monitor :as monitor]))

(defn watch-spec [paths chan]
  {:paths paths
   :handler (fn [_ e] (async/put! chan e))})

(defn loop! [update-fn wait-ms chan] ;; TODO: timeout-ms event-chan
  (async/go-loop [got-event? false]
    (let [t (async/timeout wait-ms)]
      (async/alt!
        chan ([x] (when x ; when chan is closed, loop ends.
                    (println "Read" x "from chan")
                    (recur true)))
        t (do (when got-event?
                (println "Timed out. Act upon events!")
                (update-fn)
                (println "events are resolved."))
              (recur false))))))

;;
(defn monitor
  "Start monitor and Return monitor entity"
  [update-fn wait-ms monitor-dir & more-dirs]
  (let [ch (async/chan)
        wspec (watch-spec (cons monitor-dir more-dirs) ch)]
    {::running? true
     ::watch (hawk/watch! [wspec])
     ::event-chan ch
     ::go-loop (loop! update-fn wait-ms ch)}))

(defn stop! [monitor]
  (async/close! (::go-loop monitor))
  (async/close! (::event-chan monitor))
  (hawk/stop! (::watch monitor))
  (assoc monitor ::running? false))

;;
(comment
  (add-tap (bound-fn* prn))
  (def ch (async/chan))

  (def w (hawk/watch! [(watch-spec ["./test/fixture/post-md"] ch)]))
  (hawk/stop! w)

  #_(run-monitor 4000 ch)
  #_(async/put! ch 2)

  (monitor 10 #(prn "no-op") "test/fixture/blog-v1-md")
  (monitor 100 #(prn "no-op")
           "test/fixture/blog-v1-md" "test/fixture/blog-v1-html"))