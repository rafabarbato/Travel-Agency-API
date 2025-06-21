document.addEventListener('DOMContentLoaded', () => {
    const viagensList = document.getElementById('viagens-list');
    const addViagemForm = document.getElementById('add-viagem-form');

    // URL base da sua API
    const API_URL = 'http://localhost:8080/api/viagens';

    // Credenciais para endpoints protegidos
    // Em uma aplicação real, isso seria gerenciado por um sistema de login seguro
    const credentials = btoa('admin:admin'); // Codifica "usuario:senha" em Base64

    // Função para buscar e exibir todas as viagens
    async function fetchViagens() {
        try {
            const response = await fetch(API_URL);
            if (!response.ok) {
                throw new Error('Erro ao buscar viagens!');
            }
            const viagens = await response.json();

            viagensList.innerHTML = ''; // Limpa a lista antes de adicionar os novos itens
            viagens.forEach(viagem => {
                const viagemCard = document.createElement('div');
                viagemCard.className = 'viagem-card';
                viagemCard.innerHTML = `
                    <h3>${viagem.destino}</h3>
                    <p>${viagem.descricao}</p>
                    <p><strong>Datas:</strong> ${new Date(viagem.dataPartida).toLocaleDateString()} - ${new Date(viagem.dataRetorno).toLocaleDateString()}</p>
                    <p class="price">R$ ${viagem.preco.toFixed(2)}</p>
                    <button class="delete-btn" data-id="${viagem.id}">Deletar</button>
                `;
                viagensList.appendChild(viagemCard);
            });
        } catch (error) {
            console.error(error);
            viagensList.innerHTML = '<p>Não foi possível carregar as viagens.</p>';
        }
    }

    // Função para adicionar uma nova viagem
    addViagemForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const novaViagem = {
            destino: document.getElementById('destino').value,
            dataPartida: document.getElementById('dataPartida').value,
            dataRetorno: document.getElementById('dataRetorno').value,
            preco: parseFloat(document.getElementById('preco').value),
            descricao: document.getElementById('descricao').value,
            // Valores padrão para outros campos, se necessário
            categoria: "ECONOMICA",
            vagasDisponiveis: 30
        };

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Basic ${credentials}`
                },
                body: JSON.stringify(novaViagem)
            });

            if (!response.ok) {
                throw new Error('Erro ao adicionar viagem. Verifique se a API está rodando e as credenciais estão corretas.');
            }

            addViagemForm.reset(); // Limpa o formulário
            fetchViagens(); // Atualiza a lista de viagens
        } catch (error) {
            console.error(error);
            alert(error.message);
        }
    });

    // Função para deletar uma viagem
    viagensList.addEventListener('click', async (event) => {
        if (event.target.classList.contains('delete-btn')) {
            const viagemId = event.target.dataset.id;
            
            if (confirm('Tem certeza que deseja deletar esta viagem?')) {
                try {
                    const response = await fetch(`${API_URL}/${viagemId}`, {
                        method: 'DELETE',
                        headers: {
                            'Authorization': `Basic ${credentials}`
                        }
                    });

                    if (!response.ok) {
                        throw new Error('Erro ao deletar viagem.');
                    }
                    
                    fetchViagens(); // Atualiza a lista
                } catch (error) {
                    console.error(error);
                    alert(error.message);
                }
            }
        }
    });

    // Carrega as viagens ao iniciar a página
    fetchViagens();
});