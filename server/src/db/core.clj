(ns db.core)

(require '[clojure.java.jdbc :as db])
(require '[pandect.algo.sha224 :refer :all]
         '[clojure.java.io :as io])

(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/chatdb"
               :user "root"
               :password "shoot"})

(defn register-db [nick password]
  (println "register-db was called")
  (db/insert! mysql-db :User
              {:nick nick
               :password (sha224 password)
               :sessionId (sha224 (str nick password))}))

(defn login-db [nick password]
  (println "login-db was called")
  (get (first (db/query mysql-db ["select * from User where nick = ? and password = ?" nick (sha224 password)])) :sessionid))

(defn get-sessionId [nick]
  (println "get-sessionId was called")
  (get (first (db/query mysql-db ["select sessionId from User where nick = ?" nick])) :sessionid))


