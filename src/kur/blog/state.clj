(ns kur.blog.state
  (:require [kur.blog.post :as post]
            [kur.blog.publishable :as pub]
            [kur.blog.resource :as resource]
            [kur.util.file-system :as uf]
            [babashka.fs :as fs]))

(defn state ; Remove business logic using multimethod?
  "Create state"
  [{:keys [md-dir html-dir resource-root-dirs] :as config}]
  (merge (pub/id:publishable post/post md-dir "test/fixture/tmp-html/")))

;;
(comment
  (pub/id:publishable post/post
                      "test/fixture/blog-md/" "test/fixture/tmp-html/")
  (map (fn [root-dir resource-dir]
         (pub/id:publishable resource/resource root-dir resource-dir))
       ["resource/res-roots/flat" "resource/res-roots/nested"]
       [:flat :nested]))