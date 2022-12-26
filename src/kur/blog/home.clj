(ns kur.blog.home
  (:require [babashka.fs :as fs]
            [kur.blog.publishable :refer [Publishable]]))

(defn html-file-path [html-dir] ; policy
  (str (fs/path html-dir "home.html")))

(defrecord Home [id html-dir]
  Publishable
  (out-form [this] (:html-path this))
  (public? [this] true) ; NOTE: state는 이미 먼저 upd 되어 있음을 가정
  (update! [this state]
    ;(->> md-path slurp (page-post/html nil) (spit html-path))
    ; page-home/html this ..?
    (assoc this :html-path (html-file-path html-dir))))

(defn home
  "id = home"
  [_ html-dir] (->Home "home" html-dir))