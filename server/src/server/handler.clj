(ns server.handler
  (:require [compojure.handler :refer [site]] ; form, query params decode; cookie; session, etc
            [compojure.route :as route]
            [compojure.api.core]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer [ok internal-server-error enhance-your-calm]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :as hiccup :refer [html]]
            [clojure.java.io :refer [resource]]
            [clojure.data.json :as json]
            [noir.io :as io]
            [noir.cookies :refer [put!]]
            [ring.util.response :as response]
            [clojure.pprint :as print]
            [db.core :as db]))

(defn cors-mw [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "GET, PUT, PATCH, POST, DELETE, OPTIONS")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "Authorization, Content-Type")))))

(defn custom-handler [^Exception e data request]
  (internal-server-error {:message (.getMessage e)}))

(defn calm-handler [^Exception e data request]
  (enhance-your-calm {:message (.getMessage e), :data data}))

(defn store-cookies [sessionId nick]
  (print/pprint (str "store cookies was called with session id ->" sessionId))
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "{\"reply\" : \"Cookie Success\"}" )
   :cookies {"usessionId" {:value (str sessionId "+" nick) :max-age 86400}}}
  )

(defn delete-cookies [sessionId]
  (print/pprint (str "delete cookies was called with session id ->" sessionId))
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "{\"reply\" : \"Cookie delete Success\"}" )
   :cookies {"usessionId" {:value sessionId :max-age 0}}})

(defn json-reply [fn-reply]
  (print/pprint (str "json-reply was called to deliver " fn-reply))
  (ok {:reply (json/write-str (str fn-reply))}))

(defn login-handler [nick password]
  (print/pprint (str "login-handler was called" nick password))
  (let [exists (db/login-db nick password)]
    (print/pprint (str "Does the user account exists?" exists))
    (if (zero? exists) (json-reply "Fail") (json-reply "Success"))
    ))
(defn session-handler [nick password]
  (print/pprint (str "session-handler was called" nick password))
  (let [session (db/get-sessionId nick password)]
    (if (some? session) (store-cookies session nick) (json-reply "Fail"))
    ))

(defn register-handler [nick password]
  (try
    (db/register-db nick password)
    (catch Exception e))
  (json-reply (str nick " has successfully registered")))

(defn print-request [req]
  (print/pprint req)
  )

(defapi api-routes
  {:formats [:json-kw]}
  {:exceptions {:handlers {:compojure.api.exception/default custom-handler
                           ::calm calm-handler}}}
  (GET* "/" [] (ok {:reply "Hello World from GET"}))
  (POST* "/" [] print-request)
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
  (POST* "/savesession" {params :params}
         (let [nick (:nick params)
               password (:password params)]
           (println nick password)
           (session-handler nick password)
           ))
  (POST* "/destroysession" {params :params}
         (let [nick (:nick params)
               session (db/get-session nick)]
           (if (some? session) (delete-cookies session) (json-reply "Opps"))
           ))
  (route/not-found "Not Found")
  )

;(def api-routes (-> (site #'api-routes)) ))

(defn -main []
  (print/pprint "The server is running on port 9090")
  (run-server (site #'api-routes) {:port 9090}))