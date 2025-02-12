import { createSlice } from '@reduxjs/toolkit';
import {jwtDecode} from "jwt-decode";

// Load the initial state from localStorage (if available)
const loadAuthState = () => {
    const savedAuthState = localStorage.getItem('authState');
    if (savedAuthState) {
        const parsedState = JSON.parse(savedAuthState);
        const { token } = parsedState;

        // Check if token is expired
        if (token && isTokenExpired(token)) {
            // Token is expired, remove from localStorage and return default state
            localStorage.removeItem('authState');
            return {
                isAuthenticated: false,
                user: null,
                token: null,
                error: null,
            };
        }

        return parsedState;
    }

    return {
        isAuthenticated: false,
        user: null,
        token: null,
        error: null,
    };
};

// Save the auth state to localStorage whenever it changes
const saveAuthState = (state) => {
    localStorage.setItem('authState', JSON.stringify(state));
};

// Helper function to check if the token is expired
const isTokenExpired = (token) => {
    try {
        const decodedToken = jwtDecode(token);
        // The exp value is in seconds, so we multiply it by 1000 to compare with current time in milliseconds
        return decodedToken.exp * 1000 < Date.now();
    } catch (error) {
        console.error(error);
        return true; // If decoding the token fails, assume it's expired or invalid
    }
};

const authSlice = createSlice({
    name: 'auth',
    initialState: loadAuthState(),
    reducers: {
        login: (state, action) => {
            state.isAuthenticated = true;
            state.user = action.payload.user;
            state.token = action.payload.token;
            state.error = null;
            saveAuthState(state);
        },
        logout: (state) => {
            state.isAuthenticated = false;
            state.user = null;
            state.token = null;
            localStorage.removeItem('authState');
        },
        setError: (state, action) => {
            state.error = action.payload;
            saveAuthState(state);
        },
    },
});

export const { login, logout, setError } = authSlice.actions;
export default authSlice.reducer;