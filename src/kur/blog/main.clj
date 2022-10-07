(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]
            [kur.blog.publishable :as pub]))


;;
(comment
  (def cfg (load-config :file "resource/config/static-test.edn"))
  (def s (state/state cfg))
  (def s (update-vals s #(pub/update! % s))))