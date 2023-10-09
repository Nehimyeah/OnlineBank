import { InputHTMLAttributes } from "react";
import { RefCallBack } from "react-hook-form/dist/types";

interface IClientInputProps {
  reference: RefCallBack;
}

const ClientInput = (
  props: IClientInputProps & InputHTMLAttributes<HTMLInputElement>
) => {
  const { className, reference, ...restProps } = props;
  return (
    <input
      {...restProps}
      ref={reference}
      className={`w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-indigo-500 ${
        className ?? ""
      }`}
    />
  );
};

export default ClientInput;
