(ns kur.blog.state
  (:require [kur.blog.post :as post]
            [kur.blog.publishable :as pub]
            [kur.blog.resource :as resource]
            [kur.util.file-system :as uf]
            [babashka.fs :as fs]))

(defn state
  "Create state (NOTE: This is business logic!)"
  [{:keys [md-dir html-dir resource-root-dirs]}]
  (apply merge
         (pub/id:publishable post/post md-dir html-dir)
         (map #(pub/id:publishable resource/resource %1 %2)
              resource-root-dirs resource-root-dirs)))

;;
(comment
  (pub/id:publishable post/post
                      "test/fixture/blog-md/" "test/fixture/tmp-html/")
  (map (fn [root-dir resource-dir]
         (pub/id:publishable resource/resource root-dir resource-dir))
       ["resource/res-roots/flat" "resource/res-roots/nested"]
       [:flat :nested]))