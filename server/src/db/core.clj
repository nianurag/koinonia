(ns db.core)

(require '[clojure.java.jdbc :as db])
(require '[pandect.algo.sha224 :refer :all]
         '[clojure.java.io :as io])

(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/chatdb"
               :user "root"
               :password "shoot"})

(defn current-time []
  (let [date (new java.util.Date)]
    (let [sdf (new java.text.SimpleDateFormat "yyyyMMddHHmmss")]
      (.format sdf (.getTime date)))))

(defn register-db [nick password]
  (println "register-db was called")
  (db/insert! mysql-db :User
              {:nick nick
               :password (sha224 password)
               :sessionId (sha224 (str nick (quot (System/currentTimeMillis) 1000) password))}))

; (defn login-db [nick password]
;   (println "login-db was called")
;   (get (first (db/update! mysql-db ["select * from User where nick = ? and password = ?" nick (sha224 password)])) :sessionid))

(defn login-db [nick password]
  (println "login-db was called")
  (first (db/update! mysql-db :User
                          {:sessionId (sha224 (str nick (quot (System/currentTimeMillis) 1000) password))
                           :lastLogin (current-time)}
                          ["nick = ? and password = ?" nick (sha224 password)])))

(defn get-sessionId [nick]
  (println "get-sessionId was called")
  (get (first (db/query mysql-db ["select sessionId from User where nick = ?" nick])) :sessionid))


