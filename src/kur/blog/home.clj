(ns kur.blog.home
  (:require [babashka.fs :as fs]
            [kur.blog.page.home :as page-home]
            [kur.blog.post]
            [kur.blog.publishable :refer [Publishable]]))

(defn html-file-path [html-dir] ; policy
  (str (fs/path html-dir "home.html")))

(defrecord Home [id html-dir]
  Publishable
  (out-form [this] (:html-path this))
  (public? [this] true)
  (update!  [this state] ; NOTE: state는 이미 먼저 upd 되어 있음을 가정
    (let [html-path (html-file-path html-dir)
          posts (filter #(instance? kur.blog.post.Post %) (vals state))]
      (spit html-path (page-home/html nil posts))
      (assoc this :html-path html-path))))

(defn home
  "id = home"
  [_ html-dir] (->Home "home" html-dir))