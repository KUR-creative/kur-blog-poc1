(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]
            [kur.blog.publishable :as pub]
            [kur.blog.publisher :as publisher])
  (:gen-class))

(defn -main [_]
  (let [cfg (load-config :file "resource/config/static-test.edn")
        s (state/state cfg)
        state (atom (update-vals s #(pub/update! % s)))
        handler (publisher/publisher state)]
    (publisher/start! handler cfg)))

#_(def pp (app nil));
#_(publisher/stop! pp)

;;
(comment
  (do
    (def cfg (load-config :file "resource/config/static-test.edn"))
    (def s (state/state cfg))
    (def state (atom (update-vals s #(pub/update! % s))))

    (def handler (publisher/publisher state))
    (publisher/stop! pp)
    (def pp (publisher/start! handler cfg))))