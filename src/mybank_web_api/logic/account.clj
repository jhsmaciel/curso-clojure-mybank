(ns mybank-web-api.logic.account)

(defn get-account
  "get an account from accounts map"
  [accounts account-id]
  (if (account-id accounts)
    (account-id accounts)
    (throw (ex-info "Account not found." { :type ::account-not-found }))))

(defn update-account-balance
  "update account balance from accounts"
  [accounts account-id value f]
  (get-account accounts account-id)
  (update-in accounts [account-id :saldo] #(f % value)))

(defn has-balance?
  "verify if a account has balance "
  [accounts account-id value]
  (let [account (get-account accounts account-id)]
    (if (>= (:saldo account) value)
      true
      (throw (ex-info "Account don't has balance." { :type ::account-without-balance})))))
