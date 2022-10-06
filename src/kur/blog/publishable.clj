(ns kur.blog.publishable
  (:require [kur.util.file-system :as uf]))

(defprotocol Publishable
  (response [_])
  (public? [_]))

(defn publishable [ctor path dir]
  (assoc (ctor path dir)
         :last-modified-millis (uf/last-modified-millis path)))