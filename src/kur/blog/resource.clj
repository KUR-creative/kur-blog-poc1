(ns kur.blog.resource
  (:require [kur.blog.publishable :refer [Publishable]]
            [ring.util.response :as resp]))

(defrecord Resource [id resource-dir]
  Publishable ; Obsidian과의 일치를 생각해야 한다
  (response [this] (resp/file-response id)))
