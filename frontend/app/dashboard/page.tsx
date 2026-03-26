"use client";

import useSWR from "swr";
import { api, type Account, type Transaction } from "@/lib/api";
import { formatCurrency, formatDate } from "@/lib/utils";
import {
  Wallet,
  ArrowUpRight,
  ArrowDownLeft,
  TrendingUp,
  CreditCard,
  Clock,
} from "lucide-react";

export default function DashboardPage() {
  const { data: accounts, isLoading: accountsLoading } = useSWR<Account[]>(
    "accounts",
    () => api.getAccounts()
  );

  const { data: transactions, isLoading: transactionsLoading } = useSWR<
    Transaction[]
  >("transactions", () => api.getTransactions());

  const totalBalance =
    accounts?.reduce((acc, account) => acc + account.balance, 0) || 0;

  const recentTransactions = transactions?.slice(0, 5) || [];

  if (accountsLoading || transactionsLoading) {
    return (
      <div className="flex items-center justify-center h-full">
        <div className="w-8 h-8 border-2 border-primary border-t-transparent rounded-full animate-spin" />
      </div>
    );
  }

  return (
    <div className="p-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground">Dashboard</h1>
        <p className="text-muted-foreground mt-1">
          Bem-vindo de volta! Aqui está seu resumo financeiro.
        </p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-card border border-border rounded-2xl p-6">
          <div className="flex items-center justify-between mb-4">
            <span className="text-muted-foreground text-sm font-medium">
              Saldo Total
            </span>
            <div className="w-10 h-10 bg-primary/10 rounded-xl flex items-center justify-center">
              <Wallet className="w-5 h-5 text-primary" />
            </div>
          </div>
          <p className="text-3xl font-bold text-foreground">
            {formatCurrency(totalBalance)}
          </p>
          <p className="text-sm text-muted-foreground mt-2">
            <span className="text-primary">+12%</span> desde o mês passado
          </p>
        </div>

        <div className="bg-card border border-border rounded-2xl p-6">
          <div className="flex items-center justify-between mb-4">
            <span className="text-muted-foreground text-sm font-medium">
              Contas Ativas
            </span>
            <div className="w-10 h-10 bg-accent/10 rounded-xl flex items-center justify-center">
              <CreditCard className="w-5 h-5 text-accent" />
            </div>
          </div>
          <p className="text-3xl font-bold text-foreground">
            {accounts?.length || 0}
          </p>
          <p className="text-sm text-muted-foreground mt-2">
            Contas cadastradas
          </p>
        </div>

        <div className="bg-card border border-border rounded-2xl p-6">
          <div className="flex items-center justify-between mb-4">
            <span className="text-muted-foreground text-sm font-medium">
              Transações
            </span>
            <div className="w-10 h-10 bg-accent/10 rounded-xl flex items-center justify-center">
              <TrendingUp className="w-5 h-5 text-accent" />
            </div>
          </div>
          <p className="text-3xl font-bold text-foreground">
            {transactions?.length || 0}
          </p>
          <p className="text-sm text-muted-foreground mt-2">
            Total de movimentações
          </p>
        </div>
      </div>

      {/* Recent Transactions */}
      <div className="bg-card border border-border rounded-2xl p-6">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-lg font-semibold text-foreground">
            Transações Recentes
          </h2>
          <Clock className="w-5 h-5 text-muted-foreground" />
        </div>

        {recentTransactions.length === 0 ? (
          <div className="text-center py-12">
            <div className="w-16 h-16 bg-secondary rounded-full flex items-center justify-center mx-auto mb-4">
              <ArrowLeftRight className="w-8 h-8 text-muted-foreground" />
            </div>
            <p className="text-muted-foreground">
              Nenhuma transação encontrada
            </p>
          </div>
        ) : (
          <div className="space-y-4">
            {recentTransactions.map((transaction) => (
              <div
                key={transaction.id}
                className="flex items-center justify-between p-4 bg-secondary/50 rounded-xl"
              >
                <div className="flex items-center gap-4">
                  <div
                    className={`w-10 h-10 rounded-xl flex items-center justify-center ${
                      transaction.type === "CREDIT"
                        ? "bg-primary/10"
                        : "bg-destructive/10"
                    }`}
                  >
                    {transaction.type === "CREDIT" ? (
                      <ArrowDownLeft className="w-5 h-5 text-primary" />
                    ) : (
                      <ArrowUpRight className="w-5 h-5 text-destructive" />
                    )}
                  </div>
                  <div>
                    <p className="text-sm font-medium text-foreground">
                      {transaction.type === "CREDIT"
                        ? "Recebido"
                        : "Transferido"}
                    </p>
                    <p className="text-xs text-muted-foreground">
                      {transaction.type === "CREDIT"
                        ? `De: ${transaction.sourceAccountNumber}`
                        : `Para: ${transaction.destinationAccountNumber}`}
                    </p>
                  </div>
                </div>
                <div className="text-right">
                  <p
                    className={`font-semibold ${
                      transaction.type === "CREDIT"
                        ? "text-primary"
                        : "text-destructive"
                    }`}
                  >
                    {transaction.type === "CREDIT" ? "+" : "-"}
                    {formatCurrency(transaction.amount)}
                  </p>
                  <p className="text-xs text-muted-foreground">
                    {formatDate(transaction.createdAt)}
                  </p>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
