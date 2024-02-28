/** @type {import('next').NextConfig} */
import { withContentlayer } from "next-contentlayer";

const nextConfig = {
    reactStrictMode: false,
    // 다른 Next.js 설정들을 여기에 추가할 수 있습니다.
};

export default withContentlayer(nextConfig);
