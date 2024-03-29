(ns matrix_calculator.helpers
  (:gen-class))

(defn invalid-matrix-notification []
  (throw (Exception. "Invalid matrix!")))
(defn matrices-are-not-valid-type []
  (throw (Exception. "The given matrices are not valid type! ")))
(defn index-out-of-bound []
  (throw (Exception. "The given parameters are out of bound! ")))

(defn round
  "Helper function for rounding big decimals."
  [decimals number]
  (double
    (.setScale (bigdec number) decimals java.math.RoundingMode/HALF_EVEN)))

(defn take-until
  "Takes as parameters one number x and list of elements.
  Returns an array, where first element is x first element of
  given array and the second element is an array including the rest."
  [how-many elems]
  (loop [x how-many
         elems elems
         result []
         rest-elems elems]
    (if (zero? x)
      [result (vec rest-elems)]
      (recur
       (dec x)
       (rest elems)
       (conj result (first elems))
       (rest rest-elems)))))

(defn make-matrix
  "Takes as a parameters the number of rows x, the number of columns y
  and all elements of matrix in an array.
  Composes a presentation of matrix as an array with x elements which
  presents the rows. Each row contains y elements."
  [rows columns elems]
  (if (not= (* rows columns)(count elems))
    (throw (Exception.
            (str "You should give: amount of rows, amount of columns and array of "
            (* rows columns) " elements.")))
    (loop [i rows
           elems elems
           matrix []]
      (if (zero? i)
        matrix
        (let [row-tookd (take-until columns elems)
              row (first row-tookd)
              rest-elems (second row-tookd)]
          (recur (dec i) rest-elems (conj matrix row)))))))

(defn is-matrix?
  "Tests if the given matrix is an array including array(s)."
  [matrix]
  (and (vector? matrix) (vector? (first matrix))))

(defn is-square-matrix?
  "Tests that the param matrix has as many columns as rows"
  [matrix]
  (and (= (count matrix)(count (first matrix))) (apply = (map (fn [x] (count x)) matrix))))

(defn take-column
  "Returns elements in the column of given index"
  [index matrix]
  (vec (map (fn [x] (get x index)) matrix)))

(defn get-elem
  "Returns elem in the spot matrix[y][x]."
  [matrix y x]
  (get (get matrix y) x))

(defn set-elem
  "Changes the value of index [y][x]."
  [matrix y x new-value]
  (assoc matrix y (assoc (get matrix y) x new-value)))

(defn make-empty-matrix
  "Returns y * x matrix with where is 0 in every index."
  [y x]
  (let [row (fn [x] (vec (repeat (first x) 0)))]
    (vec (map row  (repeat y [x])))))

(defn exp [x n]
  "Rises number x to it's n'th exponent x^n.
  http://en.wikipedia.org/wiki/Exponentiation"
  (loop [acc 1 n n]
    (if (zero? n) acc
        (recur (* x acc) (dec n)))))
