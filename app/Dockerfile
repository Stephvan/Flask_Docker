FROM python:3
WORKDIR /app
COPY . /app
RUN apt-get update
RUN pip install -r requirements.txt
CMD [ "python", "app.py"]
EXPOSE 80 443
