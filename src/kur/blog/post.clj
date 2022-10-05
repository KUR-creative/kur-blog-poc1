(ns kur.blog.post
  (:require [kur.blog.publishable :refer [Publishable]]
            [ring.util.response :as resp]))

(defrecord Post [id meta-str title html-dir]
  Publishable
  (response [this]
    (prn this) ; 불필요한 의존성을 피하기 위해 html을 직접 만들지는 않는다.
    (resp/file-response id)))