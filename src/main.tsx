import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import { AuthProvider } from "@/context/AuthContext";
import { QueryProvider } from "@/lib/react-query/QueryProvider";

// import App from "./App";
import { Toaster } from "./components/ui/toaster";
import AuthLayout from "./_auth/AuthLayout";
import SignupForm from "./_auth/forms/SignupForm";
import axios from "axios";
// import SigninForm from "./_auth/forms/SigninForm";

axios.defaults.baseURL = "http://localhost:8080";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BrowserRouter>
      <QueryProvider>
        <AuthProvider>
             <Routes>
        {/* public routes */}
        <Route element={<AuthLayout />}>
          {/* <Route path="/sign-in" element={<SigninForm />} /> */}
          <Route path="/sign-up" element={<SignupForm />} />
        </Route>
      </Routes>
          {/* <App /> */}
          <Toaster />
        </AuthProvider>
      </QueryProvider>
    </BrowserRouter>
  </React.StrictMode>
);
