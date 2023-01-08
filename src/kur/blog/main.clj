(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.monitor :as monitor]
            [kur.blog.state :as state])
  (:gen-class))

(defn -main [& config-path]
  (let [cfg-path (if config-path
                   (first config-path)
                   "resource/config/static-test.edn")
        cfg (load-config :file cfg-path)
        s (state/state cfg)
        state (atom (state/update! s))]
    (def state state)
    (monitor/monitor #(swap! state state/update!)
                     (:fs-wait-ms cfg)
                     (:md-dir cfg))))

(comment
  (def m (-main))
  (def m (monitor/stop! m)))


#_(def state (atom nil))
#_(defn init [_]
    (reset! state (-> (load-config :file
                                   "resource/config/static-test.edn")
                      state/state state/update!)))
#_(init nil)

#_(def app (publisher/publisher state)) ;; the handler
#_(defn -main [_]
    (init nil)
    (publisher/start! app
                      (load-config :file "resource/config/static-test.edn")))

#_(def pp (-main nil));
#_(publisher/stop! pp)

;;
(comment
  (do
    (def cfg (load-config :file "resource/config/static-test.edn"))
    (def s (-> cfg state/state state/update! atom))
    ;(def state (atom (update-vals s #(pub/update! % s))))

    ;(def handler (publisher/publisher s))
    ;(publisher/stop! pp)
    #_(def pp (publisher/start! handler cfg))))