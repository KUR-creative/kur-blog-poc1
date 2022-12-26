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
         (pub/id:publishable post/post (uf/path-seq md-dir) html-dir)
         (pub/id:publishable home/home nil html-dir)

         (map #(pub/id:publishable resource/resource
                                   (uf/path-seq %1)
                                   %2)
              resource-root-dirs resource-root-dirs)))
;;
(comment
  (pub/id:publishable post/post
                      "test/fixture/blog-md/" "test/fixture/tmp-html/")
  (map (fn [root-dir resource-dir]
         (pub/id:publishable resource/resource root-dir resource-dir))
       [(uf/path-seq "resource/res-roots/flat")
        (uf/path-seq "resource/res-roots/nested")]
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
                            "test/fixture/tmp-html/"))

  (.equals
   {"kur2207111708"
    {:id "kur2207111708",
     :meta-str nil,
     :title "Secret Manager 서비스는 어플리케이션의 secret을 안전하게 관리하여.. haha. 하드코딩된 secret을 없애고 주기적으로 자동화된 secret 변경이 가능케 한다",
     :html-dir "test/fixture/tmp-html/",
     :public? false,
     :last-modified-millis 1657527967700,
     :path
     "test/fixture/blog-md/kur2207111708.Secret Manager 서비스는 어플리케이션의 secret을 안전하게 관리하여.. haha. 하드코딩된 secret을 없애고 주기적으로 자동화된 secret 변경이 가능케 한다.md"},
    "kur2206082055"
    {:id "kur2206082055",
     :meta-str nil,
     :title "Clojure 1.10의 tap은 디버깅 용도(better prn)로 사용할 수 있다",
     :html-dir "test/fixture/tmp-html/",
     :public? false,
     :last-modified-millis 1655470637675,
     :path "test/fixture/blog-md/kur2206082055.Clojure 1.10의 tap은 디버깅 용도(better prn)로 사용할 수 있다.md"},
    "kur2004250001"
    {:id "kur2004250001",
     :meta-str "-",
     :title "오버 띵킹의 함정을 조심하라",
     :html-dir "test/fixture/tmp-html/",
     :public? false,
     :last-modified-millis 1649905086389,
     :path "test/fixture/blog-md/kur2004250001.-.오버 띵킹의 함정을 조심하라.md"},
    "kur2207281052"
    {:id "kur2207281052",
     :meta-str "+",
     :title nil,
     :html-dir "test/fixture/tmp-html/",
     :public? true,
     :last-modified-millis 1658973176383,
     :path "test/fixture/blog-md/kur2207281052.+.md"},
    "kur2207161305"
    {:id "kur2207161305",
     :meta-str "+",
     :title "kill-current-sexp의 Emacs, VSCode 구현",
     :html-dir "test/fixture/tmp-html/",
     :public? true,
     :last-modified-millis 1656926109907,
     :path "test/fixture/blog-md/kur2207161305.+.kill-current-sexp의 Emacs, VSCode 구현.md"},
    "kur2205182112"
    {:id "kur2205182112",
     :meta-str nil,
     :title ".인간의 우열(편차)보다 사용하는 도구와 환경, 문화의 우열이 퍼포먼스에 더 큰 영향을 미친다",
     :html-dir "test/fixture/tmp-html/",
     :public? false,
     :last-modified-millis 1658968984405,
     :path "test/fixture/blog-md/kur2205182112..인간의 우열(편차)보다 사용하는 도구와 환경, 문화의 우열이 퍼포먼스에 더 큰 영향을 미친다.md"}}
   (pub/id:publishable post/post
                       (uf/path-seq "test/fixture/blog-md/")
                       "test/fixture/tmp-html/"))

  (keys (state {:md-dir "test/fixture/blog-md"
                :html-dir "test/fixture/tmp-html"
                :resource-root-dirs ["resource/res-roots/flat"
                                     "resource/res-roots/nested"]})))