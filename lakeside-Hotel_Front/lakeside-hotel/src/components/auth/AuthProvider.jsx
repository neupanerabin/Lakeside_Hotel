import React, { createContext, useState, useContext } from "react";
import jwt_decode from 'jwt-decode';

// Create the AuthContext with default values
export const AuthContext = createContext({
    user: null, // Initial state for user is null
    handleLogin: (token) => {}, // Placeholder function for handling login
    handleLogout: () => {} // Placeholder function for handling logout
});

// AuthProvider component to manage authentication state
export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null); // State to hold the currently logged-in user

    // Function to handle user login
    const handleLogin = (token) => {
        try {
            console.log("Token received:", token); // Log the received token for debugging
            const decodedUser = jwt_decode(token); // Decode the JWT token to extract user information
            console.log("Decoded user:", decodedUser); // Log the decoded user for debugging

            // Store user information and token in localStorage
            localStorage.setItem("userId", decodedUser.sub); // Assuming `sub` contains the user ID
            localStorage.setItem("userRole", decodedUser.role); // Assuming `role` contains the user role
            localStorage.setItem("token", token); // Store the token itself

            setUser(decodedUser); // Set the decoded user object in state
        } catch (error) {
            console.error("Failed to decode token", error); // Log decoding error
            // Potential alternative handling if jwt_decode fails (like a manual decode fallback)
            try {
                const decodedUser = manualDecode(token);
                console.log("Manually Decoded User:", decodedUser);
                // Store user and token...
            } catch (manualError) {
                console.error("Manual decode also failed", manualError);
            }
        }
    };

    // Function to handle user logout
    const handleLogout = () => {
        // Remove user information and token from localStorage
        localStorage.removeItem("userId");
        localStorage.removeItem("userRole");
        localStorage.removeItem("token");

        setUser(null); // Reset the user state to null
    };

    // Providing user, handleLogin, and handleLogout to all children components
    return (
        <AuthContext.Provider value={{ user, handleLogin, handleLogout }}>
            {children}
        </AuthContext.Provider>
    );
};

// Custom hook to use the AuthContext
export const useAuth = () => {
    return useContext(AuthContext);
};
