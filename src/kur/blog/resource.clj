(ns kur.blog.resource
  (:require [kur.blog.publishable :refer [Publishable]]
            [ring.util.response :as resp]
            [kur.util.file-system :as uf]))

(defrecord Resource [id resource-dir]
  Publishable
  (out-form [this] id) ; id = path
  (public? [this] true)
  (update! [this _]
    (assoc this
           :last-modified-millis (uf/last-modified-millis (:path this)))))

(def resource ->Resource) ; path = id