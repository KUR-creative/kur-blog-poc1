(ns kur.blog.publishable
  (:require [kur.util.file-system :as uf]))

(defprotocol Publishable
  (response [_])
  (public? [_]))

(defn publishable [ctor path dir]
  (let [ret (ctor path dir)]
    (assoc ret
           :last-modified-millis (uf/last-modified-millis path)
           :path path
           :public? (public? ret))))