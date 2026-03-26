"use client";

import useSWR from "swr";
import { api, type Account } from "@/lib/api";
import { formatCurrency, formatCPF } from "@/lib/utils";
import { CreditCard, User, Hash, Wallet } from "lucide-react";

export default function AccountsPage() {
  const { data: accounts, isLoading } = useSWR<Account[]>("accounts", () =>
    api.getAccounts()
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
        <h1 className="text-3xl font-bold text-foreground">Contas</h1>
        <p className="text-muted-foreground mt-1">
          Visualize todas as contas cadastradas no sistema.
        </p>
      </div>

      {!accounts || accounts.length === 0 ? (
        <div className="bg-card border border-border rounded-2xl p-12 text-center">
          <div className="w-16 h-16 bg-secondary rounded-full flex items-center justify-center mx-auto mb-4">
            <CreditCard className="w-8 h-8 text-muted-foreground" />
          </div>
          <h3 className="text-lg font-semibold text-foreground mb-2">
            Nenhuma conta encontrada
          </h3>
          <p className="text-muted-foreground">
            Não há contas cadastradas no momento.
          </p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {accounts.map((account) => (
            <div
              key={account.id}
              className="bg-card border border-border rounded-2xl p-6 hover:border-primary/50 transition-all"
            >
              <div className="flex items-center justify-between mb-6">
                <div className="w-12 h-12 bg-primary/10 rounded-xl flex items-center justify-center">
                  <CreditCard className="w-6 h-6 text-primary" />
                </div>
                <span className="text-xs font-medium px-3 py-1 bg-primary/10 text-primary rounded-full">
                  Ativa
                </span>
              </div>

              <div className="space-y-4">
                <div className="flex items-center gap-3">
                  <Hash className="w-4 h-4 text-muted-foreground" />
                  <div>
                    <p className="text-xs text-muted-foreground">
                      Número da Conta
                    </p>
                    <p className="text-sm font-medium text-foreground">
                      {account.accountNumber}
                    </p>
                  </div>
                </div>

                <div className="flex items-center gap-3">
                  <User className="w-4 h-4 text-muted-foreground" />
                  <div>
                    <p className="text-xs text-muted-foreground">Titular</p>
                    <p className="text-sm font-medium text-foreground">
                      {account.customerName}
                    </p>
                  </div>
                </div>

                <div className="flex items-center gap-3">
                  <Hash className="w-4 h-4 text-muted-foreground" />
                  <div>
                    <p className="text-xs text-muted-foreground">CPF</p>
                    <p className="text-sm font-medium text-foreground">
                      {formatCPF(account.customerCpf)}
                    </p>
                  </div>
                </div>
              </div>

              <div className="mt-6 pt-6 border-t border-border">
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <Wallet className="w-4 h-4 text-muted-foreground" />
                    <span className="text-sm text-muted-foreground">Saldo</span>
                  </div>
                  <span className="text-xl font-bold text-primary">
                    {formatCurrency(account.balance)}
                  </span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
