import { useState } from "react";
import Home from "./Pages/Home/Home";
import "./App.css";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <Home></Home>
    </>
  );
}

export default App;
