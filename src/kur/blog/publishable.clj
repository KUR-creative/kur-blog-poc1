(ns kur.blog.publishable)

(defprotocol Publishable
  (response [_]))