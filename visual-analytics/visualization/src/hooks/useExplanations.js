import { useEffect, useState } from "react";

export default function useExplanations() {
  const [explanations, setExplanations] = useState({});

  useEffect(() => {
    fetch("./fraud_explanations.json")
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Server returned ${res.status} ${res.statusText}`);
        }

        const contentType = res.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
          throw new TypeError(
            "Oops, we didn't get JSON! Check if the file exists in the public folder."
          );
        }

        return res.json();
      })
      .then((data) => {
        const map = {};

        data?.forEach((item) => {
          map[item.node_id] = {
            reasons: item.reasons,
          };
        });
        setExplanations(map);
      })
      .catch((err) => {
        console.error("Failed to load explanations:", err.message);
      });
  }, []);

  return explanations;
}
