(ns server.handler
  (:require [compojure.handler :refer [site]] ; form, query params decode; cookie; session, etc
            [compojure.route :as route]
            ;[compojure.api.core :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer [ok]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :as hiccup :refer [html]]
            [clojure.java.io :refer [resource]]
            [clojure.data.json :as json]
            [noir.io :as io]
            [noir.response :as response]
            [clojure.pprint :as print]))

(defn cors-mw [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "GET, PUT, PATCH, POST, DELETE, OPTIONS")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "Authorization, Content-Type")))))

(defn login [request]
  (print/pprint request)
  (ok {:reply (json/write-str (request :params))}))

(defapi api-routes
  {:formats [:json-kw]}
  (GET* "/" [] (ok {:reply "Hello World from GET"}))
  (POST* "/" [] (ok {:reply "Hello World from POST"}))
  (POST* "/login" [] login)
  (route/not-found "Not Found")
  )

(defn -main []
  (print/pprint "The server is running on port 9090")
  (run-server (site #'api-routes) {:port 9090}))