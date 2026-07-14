import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';
import App from './App.tsx';
import './index.css';

// Global fetch interceptor to dynamically proxy API requests in production
const originalFetch = window.fetch;
const customFetch = function (input: any, init: any) {
  let url = typeof input === 'string' ? input : input instanceof URL ? input.toString() : (input as Request).url;
  const apiUrl = (import.meta as any).env.VITE_API_URL;
  if (apiUrl && url.startsWith('/api')) {
    url = `${apiUrl.replace(/\/$/, '')}${url}`;
  }
  if (typeof input === 'string') {
    return originalFetch(url, init);
  } else if (input instanceof URL) {
    return originalFetch(new URL(url), init);
  } else {
    const newRequest = new Request(url, input);
    return originalFetch(newRequest, init);
  }
};

try {
  Object.defineProperty(window, 'fetch', {
    value: customFetch,
    configurable: true,
    writable: true,
    enumerable: true
  });
} catch (err) {
  console.warn('Could not override window.fetch via Object.defineProperty. Falling back to direct assignment.', err);
  try {
    (window as any).fetch = customFetch;
  } catch (err2) {
    console.error('Failed to intercept fetch requests.', err2);
  }
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
);

