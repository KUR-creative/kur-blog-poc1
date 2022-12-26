(ns kur.blog.publishable
  (:require [kur.util.file-system :as uf]
            [clojure.core :as core]
            [babashka.fs :as fs]))

(defprotocol Publishable
  (out-form [_])
  (public? [_])
  (update! [_ state]))

(defn publishable
  "ctor wrapper for uniform interface(:public? ..)
   inp is input(post path -> kurXX, res path -> as-is) to create Publishable.
   out-dir is directory path where output files be saved."
  [ctor inp out-dir]
  (let [ret (ctor inp out-dir)
        ret (assoc ret :public? (public? ret))]
    (assert (:id ret)
            (format "id = %s, publishable id must be identifiable: %s"
                    (:id ret) (print-str ret)))
    (if (and (some? inp) (fs/exists? inp))
      (assoc ret
             :last-modified-millis (uf/last-modified-millis inp)
             :path inp)
      ret)))

(defn id:publishable
  ([ctor root dir]
   (let [pubs (mapv #(publishable ctor % dir) (uf/path-seq root))
         ret-m (zipmap (map :id pubs) pubs)]
     (assert (= (count pubs) (count ret-m))
             (format "merged result has same N of pubs, but %d != %d"
                     (count pubs) (count ret-m)))
     ret-m)))