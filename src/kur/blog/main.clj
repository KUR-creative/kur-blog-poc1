(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]))


;;
(comment
  (def cfg (load-config :file "resource/config/static-test.edn"))
  (state/state cfg))