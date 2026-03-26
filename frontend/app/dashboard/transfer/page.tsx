"use client";

import { useState } from "react";
import useSWR, { mutate } from "swr";
import { api, type Account } from "@/lib/api";
import { formatCurrency } from "@/lib/utils";
import { ArrowRight, Loader2, CheckCircle, AlertCircle } from "lucide-react";

export default function TransferPage() {
  const { data: accounts, isLoading } = useSWR<Account[]>("accounts", () =>
    api.getAccounts()
  );

  const [sourceAccount, setSourceAccount] = useState("");
  const [destinationAccount, setDestinationAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState("");

  const handleAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.replace(/\D/g, "");
    const numericValue = parseInt(value) / 100;
    if (!isNaN(numericValue)) {
      setAmount(numericValue.toFixed(2));
    } else {
      setAmount("");
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSuccess(false);
    setIsSubmitting(true);

    try {
      await api.transfer({
        sourceAccountNumber: sourceAccount,
        destinationAccountNumber: destinationAccount,
        amount: parseFloat(amount),
      });

      setSuccess(true);
      setSourceAccount("");
      setDestinationAccount("");
      setAmount("");

      // Revalidate data
      mutate("accounts");
      mutate("transactions");
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Erro ao realizar transferência"
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  const selectedSource = accounts?.find(
    (a) => a.accountNumber === sourceAccount
  );

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-full">
        <div className="w-8 h-8 border-2 border-primary border-t-transparent rounded-full animate-spin" />
      </div>
    );
  }

  return (
    <div className="p-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground">Transferir</h1>
        <p className="text-muted-foreground mt-1">
          Realize transferências entre contas de forma rápida e segura.
        </p>
      </div>

      <div className="max-w-2xl">
        <div className="bg-card border border-border rounded-2xl p-8">
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Source Account */}
            <div>
              <label
                htmlFor="source"
                className="block text-sm font-medium text-foreground mb-2"
              >
                Conta de origem
              </label>
              <select
                id="source"
                value={sourceAccount}
                onChange={(e) => setSourceAccount(e.target.value)}
                className="w-full px-4 py-3 bg-secondary border border-border rounded-xl text-foreground focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all appearance-none cursor-pointer"
                required
              >
                <option value="">Selecione uma conta</option>
                {accounts?.map((account) => (
                  <option key={account.id} value={account.accountNumber}>
                    {account.accountNumber} - {account.customerName} (
                    {formatCurrency(account.balance)})
                  </option>
                ))}
              </select>
              {selectedSource && (
                <p className="text-sm text-muted-foreground mt-2">
                  Saldo disponível:{" "}
                  <span className="text-primary font-medium">
                    {formatCurrency(selectedSource.balance)}
                  </span>
                </p>
              )}
            </div>

            {/* Arrow indicator */}
            <div className="flex justify-center">
              <div className="w-10 h-10 bg-secondary rounded-full flex items-center justify-center">
                <ArrowRight className="w-5 h-5 text-muted-foreground rotate-90" />
              </div>
            </div>

            {/* Destination Account */}
            <div>
              <label
                htmlFor="destination"
                className="block text-sm font-medium text-foreground mb-2"
              >
                Conta de destino
              </label>
              <select
                id="destination"
                value={destinationAccount}
                onChange={(e) => setDestinationAccount(e.target.value)}
                className="w-full px-4 py-3 bg-secondary border border-border rounded-xl text-foreground focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all appearance-none cursor-pointer"
                required
              >
                <option value="">Selecione uma conta</option>
                {accounts
                  ?.filter((a) => a.accountNumber !== sourceAccount)
                  .map((account) => (
                    <option key={account.id} value={account.accountNumber}>
                      {account.accountNumber} - {account.customerName}
                    </option>
                  ))}
              </select>
            </div>

            {/* Amount */}
            <div>
              <label
                htmlFor="amount"
                className="block text-sm font-medium text-foreground mb-2"
              >
                Valor
              </label>
              <div className="relative">
                <span className="absolute left-4 top-1/2 -translate-y-1/2 text-muted-foreground">
                  R$
                </span>
                <input
                  type="text"
                  id="amount"
                  value={amount}
                  onChange={handleAmountChange}
                  placeholder="0,00"
                  className="w-full pl-12 pr-4 py-3 bg-secondary border border-border rounded-xl text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all text-lg font-medium"
                  required
                />
              </div>
            </div>

            {/* Success Message */}
            {success && (
              <div className="flex items-center gap-3 bg-primary/10 border border-primary/20 text-primary px-4 py-3 rounded-xl">
                <CheckCircle className="w-5 h-5" />
                <span className="text-sm font-medium">
                  Transferência realizada com sucesso!
                </span>
              </div>
            )}

            {/* Error Message */}
            {error && (
              <div className="flex items-center gap-3 bg-destructive/10 border border-destructive/20 text-destructive px-4 py-3 rounded-xl">
                <AlertCircle className="w-5 h-5" />
                <span className="text-sm font-medium">{error}</span>
              </div>
            )}

            {/* Submit Button */}
            <button
              type="submit"
              disabled={isSubmitting || !sourceAccount || !destinationAccount || !amount}
              className="w-full bg-primary text-primary-foreground font-semibold py-4 px-4 rounded-xl hover:bg-primary/90 focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2 focus:ring-offset-background disabled:opacity-50 disabled:cursor-not-allowed transition-all flex items-center justify-center gap-2"
            >
              {isSubmitting ? (
                <>
                  <Loader2 className="w-5 h-5 animate-spin" />
                  Processando...
                </>
              ) : (
                <>
                  <ArrowRight className="w-5 h-5" />
                  Transferir
                </>
              )}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
