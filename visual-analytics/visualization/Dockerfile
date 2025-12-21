FROM node:18-alpine AS build
WORKDIR /app

COPY visual-analytics/visualization/package*.json ./
RUN npm install


COPY . .


WORKDIR /app/visual-analytics/visualization
RUN npm run build

FROM nginx:alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf


COPY --from=build /app/visual-analytics/visualization/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]