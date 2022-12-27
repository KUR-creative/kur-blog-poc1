(ns kur.blog.page.home
  (:require
   [hiccup.page :refer [html5]]
   [hiccup.element :refer [link-to]]
   [kur.blog.page.template :refer [head]]))

(defn post-url [post-id]
  (str "http://" "localhost"
       ":" 3000
       "/" post-id))

(defn post-list
  [posts]
  (def posts posts)

  (into [:ul]
        (map (fn [{:keys [id title]}]
               [:li (link-to (post-url id) (str title))])
             posts)))

(defn html
  "posts is seq of Post Publishable"
  [css-paths posts]
  (html5 (head :css-paths css-paths)
         [:body (post-list posts)]))