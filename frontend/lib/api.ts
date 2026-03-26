const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

interface LoginRequest {
  cpf: string;
  password: string;
}

interface LoginResponse {
  token: string;
}

interface Account {
  id: number;
  accountNumber: string;
  balance: number;
  customerName: string;
  customerCpf: string;
}

interface Transaction {
  id: number;
  amount: number;
  type: string;
  sourceAccountNumber: string;
  destinationAccountNumber: string;
  createdAt: string;
}

interface TransferRequest {
  sourceAccountNumber: string;
  destinationAccountNumber: string;
  amount: number;
}

function getToken(): string | null {
  if (typeof window === "undefined") return null;
  return localStorage.getItem("token");
}

async function fetchWithAuth(url: string, options: RequestInit = {}) {
  const token = getToken();
  const headers: HeadersInit = {
    "Content-Type": "application/json",
    ...options.headers,
  };

  if (token) {
    (headers as Record<string, string>)["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${url}`, {
    ...options,
    headers,
  });

  if (response.status === 401) {
    if (typeof window !== "undefined") {
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    throw new Error("Unauthorized");
  }

  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || "Request failed");
  }

  return response.json();
}

export const api = {
  login: async (data: LoginRequest): Promise<LoginResponse> => {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error("CPF ou senha inválidos");
    }

    return response.json();
  },

  getAccounts: (): Promise<Account[]> => {
    return fetchWithAuth("/accounts");
  },

  getTransactions: (): Promise<Transaction[]> => {
    return fetchWithAuth("/transactions");
  },

  transfer: (data: TransferRequest): Promise<Transaction> => {
    return fetchWithAuth("/transactions/transfer", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },
};

export type { Account, Transaction, TransferRequest, LoginRequest, LoginResponse };
