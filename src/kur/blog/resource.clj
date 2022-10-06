(ns kur.blog.resource
  (:require [kur.blog.publishable :refer [Publishable]]
            [ring.util.response :as resp]
            [kur.blog.resource :as resource]))

(defrecord Resource [id resource-dir]
  Publishable
  (response [this] (resp/file-response id))
  (public? [this] (:public? this)))

(def resource ->Resource) ; path = id