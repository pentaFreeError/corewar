FROM openjdk:17-jdk-buster

RUN apt-get update && apt-get install -y \
    libxext6 libxrender1 libxtst6 libxi6 libx11-6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app/source

COPY . /app

RUN chmod +x corewar.sh

ENV DISPLAY=:0

CMD ["sh", "corewar.sh"]

