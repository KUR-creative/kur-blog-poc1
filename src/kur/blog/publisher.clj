(ns kur.blog.publisher
  (:require ;[reitit.core :as r]
   [reitit.ring :as ring]
   [ring.adapter.jetty :refer [run-jetty]]
   [ring.util.response :as resp]))

;; tmp
(defn home [req]
  (def req req)
  {:status 200 :body "home"})

(defn post [req]
  (def req req)
  #_(resp/file-response)
  {:status 200 :body "post"})

(defn not-found [req]
  (def req req)
  {:status 404 :body "404 NOT FOUND"})

;;
(def router
  (ring/router [["/" {:get home}]
                ["/home" {:get home}]
                ["/kur*" {:get post}]])) ; id = kur0000000000
                ;; NOTE: 이는 저자가 kur만임을 가정한다... 

(def app
  (ring/ring-handler
   router
   (ring/routes
    (ring/redirect-trailing-slash-handler {:method :strip})
    not-found)))

(app {:request-method :get :uri "/kur1"})

;;
(.stop s)
(def s (run-jetty app {:port 8080 :join? false}))