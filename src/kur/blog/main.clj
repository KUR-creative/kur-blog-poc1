(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]
            [kur.blog.publishable :as pub]
            [kur.blog.publisher :as publisher]))


;;
(comment
  (do
    (def cfg (load-config :file "resource/config/static-test.edn"))
    (def s (state/state cfg))
    (def state (atom (update-vals s #(pub/update! % s)))))

  (do
    (def handler (publisher/publisher state))
    (publisher/stop! pp)
    (def pp (publisher/start! handler cfg))))