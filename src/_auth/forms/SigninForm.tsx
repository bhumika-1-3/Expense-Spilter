import * as z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link, useNavigate } from "react-router-dom";

import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import Loader from "@/components/shared/Loader";

import { SigninValidation } from "@/lib/validation";
// import { useUserContext } from "@/context/AuthContext";
import { toast } from "@/components/ui";
import { useMutation } from "@tanstack/react-query";
import axios from "axios";

const SigninForm = () => {
  const navigate = useNavigate();
  // const { checkAuthUser, isLoading: isUserLoading } = useUserContext();
  
  const { mutateAsync: signInAccount, isLoading } = useMutation<
    any, // result type (response)
    Error, // error type
    z.infer<typeof SigninValidation> // payload/variables type  🔥 the important one
  >({
    mutationFn: (payload) => axios.post("/user/login", payload),
  });

  const form = useForm<z.infer<typeof SigninValidation>>({
    resolver: zodResolver(SigninValidation),
    defaultValues: {
      name: "",
      password: "",
    },
  });

  const handleSignin = async (user: z.infer<typeof SigninValidation>) => {
    const session = await signInAccount(user);
    console.log(session.data);
    if (!session) {
      toast({ title: "Login failed. Please try again." });
      return;
    }

    // const isLoggedIn = await checkAuthUser();

    if (session.status === 200) {
      toast({ title: "Login Successfully." });
      localStorage.setItem("access", session.data.accessToken);
      localStorage.setItem("refresh", session.data.refreshToken);
      form.reset();
      navigate("/");
    } else {
      toast({ title: "Login failed. Please try again." });
    }
  };

  return (
    <Form {...form}>
      <div className="bg-gray-800 h-16 fixed top-0 w-full z-10">
        <div className="text-white font-bold text-lg flex items-center justify-center">
          <span
            role="img"
            aria-label="Splitwise App"
            className="mr-3 p-2 mt-1 items-center">
            <img
              width="40"
              height="40"
              src="/assets/images/split-logo.png"
              alt="cash-in-hand"
            />
          </span>
          Splitwise
        </div>
      </div>

      <div className="sm:w-420 flex-center flex-col">
        <h3 style={{ color: "#1CC29F" }} className="text-2xl font-bold">
          Login
        </h3>
        {/* Add your login form components here */}
        <form
          onSubmit={form.handleSubmit(handleSignin)}
          className="flex flex-col gap-5 w-full mt-4">
          <FormField
            control={form.control}
            name="name"
            render={({ field }) => (
              <FormItem>
                <FormLabel className="shad-form_label">Name</FormLabel>
                <FormControl>
                  <Input type="text" className="shad-input" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormLabel className="shad-form_label">Password</FormLabel>
                <FormControl>
                  <Input type="password" className="shad-input" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <Button
            type="submit"
            style={{ backgroundColor: "#1CC29F" }}
            className="">
            {isLoading? (
              <div className="flex-center gap-2">
                <Loader /> Loading...
              </div>
            ) : (
              "Log in"
            )}
          </Button>

          <p className="text-small-regular text-light-2 text-center mt-2">
            Don&apos;t have an account?
            <Link
              style={{ color: "#1CC29F" }}
              to="/sign-up"
              className=" text-small-semibold ml-1">
              Sign up
            </Link>
          </p>
        </form>
      </div>
    </Form>
  );
};

export default SigninForm;
