(ns server.handler
  (:require [compojure.handler :refer [site]] ; form, query params decode; cookie; session, etc
            [compojure.route :as route]
            ;[compojure.api.core :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer [ok]]
            [org.httpkit.server :refer [run-server]]
            ;[hiccup.core :as hiccup :refer [html]]
            [clojure.java.io :refer [resource]]
            [clojure.data.json :as json]
            ;[noir.io :as io]
            ;[noir.response :as response]
            [clojure.pprint :as print]
            [db.core :as db]))

(defn cors-mw [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "GET, PUT, PATCH, POST, DELETE, OPTIONS")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "Authorization, Content-Type")))))

(defn json-reply [fn-reply]
  (print/pprint (str "json-reply was called to deliver " fn-reply))
  (ok {:reply (json/write-str (str fn-reply))}))

(defn login-handler [nick password]
  (print/pprint (str "login-handler was called" nick password))
  (let [return (db/login-db nick password)]
    (print/pprint (str "The return value from the db is" return))
    (if (some? return) return (json-reply "Fail"))
    (if (not (some? return)) return (json-reply "Success"))
    ))

(defn register-handler [nick password]
  (try
    (db/register-db nick password)
    (catch Exception e))
  (json-reply (str nick " has successfully registered")))

(defapi api-routes
  {:formats [:json-kw]}
  (GET* "/" [] (ok {:reply "Hello World from GET"}))
  (POST* "/" [] (ok {:reply "Hello World from POST"}))
  (POST* "/login" {params :params}
         (let [nick (:nick params)
               password (:password params)]
           (println nick password)
           (login-handler nick password)
           ))
  (POST* "/register" {params :params}
         (let [nick (:nick params)
               password (:password params)]
           (println nick password)
           (register-handler nick password)
           ))
  (route/not-found "Not Found")
  )

(defn -main []
  (print/pprint "The server is running on port 9090")
  (run-server (site #'api-routes) {:port 9090}))