(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]
            [kur.blog.publishable :as pub]
            [kur.blog.publisher :as publisher])
  (:gen-class))

(def state (atom nil))
(defn init [_]
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