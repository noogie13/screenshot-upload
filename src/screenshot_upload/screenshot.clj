(ns backend.utils
  (:require [clojure.java.io :as io]
            [hiccup.core :refer :all]))

(def files (atom {}))

(defonce characters "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

(defn random-chars
  [n]
  (->> (repeatedly #(rand-nth characters))
       (take n)
       (reduce str)))

(defn unique-id
  []
  (let [new-id (random-chars (+ 4 (rand-int 4)))]
    (if (@files new-id)
      (unique-id)
      new-id)))

(defn file-upload
  [req]
  (let* [id (unique-id)
         file (get (req :params) "file")]
    (swap! files merge @files {(keyword id)
                               [(get (req :params) "type")
                                (:tempfile file)]})
    id))

(defn list-files
  []
  (html
   [:ul
    (map (fn [[k v]] [:li (link-to (str "/" (name k))
                                   (name k))]) @files)]))

(defn access-file
  [key]
  (let [data (@files (keyword key))]
    {:status 200
     :headers {"Content-Type" (first data)}
     :body (second data)}))
