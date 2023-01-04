(ns kur.blog.main
  (:require [cprop.core :refer [load-config]]
            [kur.blog.state :as state]
            [kur.blog.publishable :as pub]
            [kur.blog.publisher :as publisher])
  (:gen-class))

(def app
  (let [cfg-path "resource/config/static-test.edn"
        s (state/state (load-config :file cfg-path))
        state (atom (update-vals s #(pub/update! % s)))
        ;; 현재 pub update!에 순서가 없어도 되서 문제되지 않음
        ;; 만일 update!에 순서가 필요하다면 update-vals말고 직접 짜야 함
        ]
    (publisher/publisher state)))

(defn -main [_]
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