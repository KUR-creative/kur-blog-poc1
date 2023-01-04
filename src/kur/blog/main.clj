(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]
            [kur.blog.publishable :as pub]
            [kur.blog.publisher :as publisher])
  (:gen-class))

(def state (atom nil))

(defn init [_]
  (let [cfg-path "resource/config/static-test.edn"
        s (state/state (load-config :file cfg-path))]
    (reset! state (update-vals s #(pub/update! % s)))))
;; 현재 pub update!에 순서가 없어도 되서 문제되지 않음
;; 만일 update!에 순서가 필요하다면 update-vals말고 직접 짜야 함
;;
;; 이후 update! 부분을 빼거나(updater / publisher 구분)
;; nginx에서 shared hash-map인가.. 써서 최적화 할 수도 있긴 할 듯??

(def app (publisher/publisher state)) ;; the handler

(defn -main [_]
  (init nil)
  (publisher/start! app
                    (load-config :file "resource/config/static-test.edn")))

#_(def pp (-main nil));
#_(publisher/stop! pp)

;;
(comment
  (do
    (def cfg (load-config :file "resource/config/static-test.edn"))
    (def s (state/state cfg))
    (def state (atom (update-vals s #(pub/update! % s))))

    (def handler (publisher/publisher state))
    (publisher/stop! pp)
    (def pp (publisher/start! handler cfg))))