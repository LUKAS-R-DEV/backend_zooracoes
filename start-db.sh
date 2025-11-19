#!/bin/bash

echo "🚀 Iniciando banco de dados PostgreSQL..."

# Verificar se docker-compose está disponível
if command -v docker-compose &> /dev/null; then
    echo "📦 Usando Docker Compose..."
    
    # Tentar iniciar sem sudo primeiro
    if docker-compose up -d 2>/dev/null; then
        echo "✅ Banco de dados iniciado com sucesso!"
        echo "📊 Verificando status..."
        docker-compose ps
    else
        echo "⚠️  Tentando com sudo..."
        sudo docker-compose up -d
        if [ $? -eq 0 ]; then
            echo "✅ Banco de dados iniciado com sucesso!"
            sudo docker-compose ps
        else
            echo "❌ Erro ao iniciar o banco. Verifique as permissões do Docker."
            echo "💡 Dica: Adicione seu usuário ao grupo docker:"
            echo "   sudo usermod -aG docker $USER"
            echo "   (Depois faça logout e login novamente)"
        fi
    fi
else
    echo "❌ docker-compose não encontrado."
    echo "💡 Instale o Docker Compose ou use PostgreSQL local."
fi

echo ""
echo "🔍 Para verificar se está rodando:"
echo "   docker-compose ps"
echo ""
echo "🛑 Para parar o banco:"
echo "   docker-compose down"

