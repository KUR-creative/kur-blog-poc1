(ns kur.blog.state
  (:require [kur.blog.post :as post]
            [kur.blog.publishable :as pub]
            [kur.blog.resource :as resource]
            [kur.blog.home :as home]
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
       [:flat :nested])



  (pub/publishable home/home nil "test/fixture/tmp-html/")
  (.equals {:id "kur2207281052",
            :meta-str "+",
            :title nil,
            :html-dir "test/fixture/tmp-html/",
            :last-modified-millis 1658973176383,
            :path "test/fixture/blog-md/kur2207281052.+.md",
            :public? true}
           (pub/publishable post/post
                            "test/fixture/blog-md/kur2207281052.+.md"
                            "test/fixture/tmp-html/")))