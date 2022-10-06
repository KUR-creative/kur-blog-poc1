(ns unit.kur.blog.publishable-test
  (:require
   [clojure.test :refer [is deftest run-tests]]
   [kur.blog.publishable :as p]
   [kur.blog.post :as post]
   [kur.blog.resource :as resource]))

(deftest ctor-test
  (is (some? (p/publishable post/post
                            "test/fixture/blog-md/kur2207281052.+.md"
                            "test/fixture/tmp-html/")))
  (is (some? (p/publishable resource/resource
                            "test/fixture/blog-md/kur2207281052.+.md"
                            "test/fixture/tmp-html/"))))