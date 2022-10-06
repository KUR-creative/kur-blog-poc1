(ns kur.blog.publishable
  (:require [kur.util.file-system :as uf]))

(defprotocol Publishable
  (response [_])
  (public? [_]))

(defn publishable [ctor path dir]
  (let [ret (ctor path dir)]
    (if (:id ret)
      (assoc ret
             :last-modified-millis (uf/last-modified-millis path)
             :path path
             :public? (public? ret))
      (throw (Exception. (format ":id = %s, id must be identifiable"
                                 (:id ret)))))))

(defn id:publishable
  ([ctor root dir]
   (let [pubs (mapv #(publishable ctor % dir) (uf/path-seq root))
         ret-m (zipmap (map :id pubs) pubs)]
     (assert (= (count pubs) (count ret-m))
             (format "merged result has same N of pubs, but %d != %d"
                     (count pubs) (count ret-m)))
     ret-m)))