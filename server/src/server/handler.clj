(ns server.handler
  (:require [compojure.handler :refer [site]] ; form, query params decode; cookie; session, etc
            [compojure.route :as route]
            ;[compojure.api.core :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer [ok]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :as hiccup :refer [html]]
            [clojure.java.io :refer [resource]]
            [noir.io :as io]
            [noir.response :as response]
            [clojure.pprint :as print]))

(defapi api-routes
  {:formats [:json-kw]}
  (GET* "/" [] (ok {:reply "Hello World"}))
  (route/not-found "Not Found")
  )

(defn -main []
  (print/pprint "The server is running on port 9090")
  (run-server (site #'api-routes) {:port 9090}))