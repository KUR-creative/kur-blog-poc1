(ns kur.blog.publisher
  (:require ;[reitit.core :as r]
   [babashka.fs :as fs]
   [kur.blog.post :as post]
   [kur.blog.publishable :as pub]
   [reitit.ring :as ring]
   [ring.adapter.jetty :refer [run-jetty]]
   [ring.util.response :as resp]))

;; tmp
(defn home [req]
  (def req req)
  {:status 200 :body "home"})

(defn not-found [req]
  (def req req)
  {:status 404 :body "404 NOT FOUND"})

(defn publishable-id
  "post-id or resource-id"
  [uri]
  (let [id-str (subs uri 1)] ; Remove first slash '/'
    (if-let [post-id (:id (post/fname->parts id-str))]
      post-id
      id-str)))

(defn publish [state]
  (fn [req]
    (def req req)
    (when-let [pub (@state (publishable-id (:uri req)))]
      (def pub pub)
      (resp/file-response (pub/out-form pub)))))

(defn publisher [state]
  (ring/ring-handler
   (ring/router [["/" {:get home}]
                 ["/home" {:get home}]
            ;; id = kur0000000000, NOTE: 저자가 kur임을 가정 
                 ["/kur*" {:get (publish state)}]])
   (ring/routes
    (ring/redirect-trailing-slash-handler {:method :strip})
    not-found)))

(defn start! [handler {:keys [port]}]
  (run-jetty handler {:port port :join? false}))

(defn stop! [jetty-server] (.stop jetty-server))

(comment
;;
  (def router
    (ring/router
     [["/" {:get home}]
      ["/home" {:get home}]
      ["/index.html" {:get home}]
      ["/about" {:get (fn [req] {:status 200 :body "about"})}]
      ["/kur*" {:get (fn [req] {:status 200 :body "post"})}]
      ["/resource/*" {:get (fn [req] {:status 200 :body "res"})}]]))

  (def app
    (ring/ring-handler
     router
     (ring/routes
      (ring/redirect-trailing-slash-handler {:method :strip})
      not-found)))

  (app {:request-method :get :uri "nf"})
  (app {:request-method :get :uri "/"})
  (app {:request-method :get :uri "/home"})
  (app {:request-method :get :uri "/index.html"})
  (app {:request-method :get :uri "/about"})
  (app {:request-method :get :uri "/kur1234"})
  (app {:request-method :get :uri "/kur12"})
  (app {:request-method :get :uri "/resource/asdf"})

;;
  )