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
            [noir.response :as response]
            [clojure.pprint :as print]
            [db.core :as db]))

(defn cors-mw [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "GET, PUT, PATCH, POST, DELETE, OPTIONS")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "Authorization, Content-Type")))))

(defn set-cookie
  "Sets a cookie on the response. Requires the handler to be wrapped in the
  wrap-cookies middleware."
  [resp name value & [opts]]
  (assoc-in resp [:cookies name] (merge {:value value} opts)))

(defn json-reply [fn-reply]
  (print/pprint (str "json-reply was called to deliver " fn-reply))
  (ok {:reply (json/write-str (str fn-reply))}))

(defn login-handler [nick password]
  (print/pprint (str "login-handler was called" nick password))
  (let [session (db/login-db nick password)]
    (print/pprint (str "The session id of the user is " session))
    (if (some? session) session (json-reply "Fail"))
    (if (not (some? session)) session (json-reply "Success"))
    ))

;(defn store-cookies []
 ; )

(defn register-handler [nick password]
  (try
    (db/register-db nick password)
    (catch Exception e))
  (json-reply (str nick " has successfully registered")))

(defn print-request [request]
  (print/pprint request))

(defapi api-routes
  {:formats [:json-kw]}
  (GET* "/" [] (ok {:reply "Hello World from GET"}))
  (POST* "/" [] (print-request))
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
  ;(POST* "/savesession" {params :params })
  (route/not-found "Not Found")
  )

(defn -main []
  (print/pprint "The server is running on port 9090")
  (run-server (site #'api-routes) {:port 9090}))