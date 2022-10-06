(ns kur.util.file-system
  (:require [babashka.fs :as fs]
            [clojure.spec.alpha :as s]
            [clojure.java.io :as io]))

;;;
(s/def ::file-name ;; not a root
  (s/and #(not (#{"" "." ".."} (str %)))
         #(not (.contains (str %) "/"))))

(s/def ::path #(not= (str %) ""))
;; NOTE: Valid unix path are way too robust.
;; See https://unix.stackexchange.com/questions/125522/path-syntax-rules
;; Maybe . .. / /// ~ etc.. are need to be supported. But not now!

(s/def ::extension
  (s/and #(not (.contains (str %) "/")) #(not= (first (str %)) \.)))

(s/def ::existing-path (s/and ::path fs/exists?))

;;;
(defn delete-all-except-gitkeep [dir]
  (->> (fs/list-dir dir)
       (remove #(= (fs/file-name %) ".gitkeep"))
       (run! fs/delete)))

(defn last-modified-millis [path]
  (-> path fs/last-modified-time fs/file-time->millis))

;;;
(defn path-seq
  "TODO: Use transducer instead? (perf)"
  ([root] (path-seq root #(.isFile %) str))
  ([root pred] (path-seq root pred str))
  ([root pred f] (->> (file-seq (io/as-file root))
                      (filter pred) (map f))))

(comment
  (path-seq "resource/res-roots/")
  (path-seq "resource/res-roots/" #(.isDirectory %))
  (path-seq "resource/res-roots/" #(.isFile %) identity))